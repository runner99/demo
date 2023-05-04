package com.runner99.vo;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.*;


public class Tree {

    private static Long[] result;

    //构建树（基于根据权重排好序的身份列表）
    public static ArrayList<Level> getTreeList(ArrayList<IdentityVo> identityVos) {

        ArrayList<Level> tree = new ArrayList<>();

        List<String> attributes = fieldsToString(IdentityVo.class.getDeclaredFields());

        //遍历身份属性
        attributes.stream().forEach(attribute -> {
            //层
            Level level = new Level();
            level.setLevelName(attribute);

            HashMap<String, String> attributeMap = new HashMap<>();

            //首先先把层的所有节点都创建出来
            Set<String> levelAttributesValues = getLevelAttributes(attribute, identityVos);
            levelAttributesValues.stream().forEach(levelAttributesValue -> {
                if (levelAttributesValue != null) {
                    HashMap<String, Node> stringNodeHashMap = new HashMap<>();
                    Node node = new Node();
                    node.setKey(levelAttributesValue);
                    stringNodeHashMap.put(levelAttributesValue, node);
                    Map<String, Node> levelValue = level.getLevelValue();
                    levelValue.put(levelAttributesValue, node);
                } else {
                    HashMap<String, Node> stringNodeHashMap = new HashMap<>();
                    Node node = new Node();
                    node.setKey(levelAttributesValue);
                    stringNodeHashMap.put(levelAttributesValue, node);
                    level.getNullLevelValue().put(levelAttributesValue, node);
                }
                attributeMap.put(levelAttributesValue, "");
            });

            identityVos.stream().forEach(identityVo -> {

                try {
                    //获取属性值
                    Field field = identityVo.getClass().getDeclaredField(attribute);
                    field.setAccessible(true);
                    String attributeValue = (String) field.get(identityVo);

                    // 如果这个属性值为空，则这层的所有节点都命中（都加一）
                    if (attributeValue == null) {
                        Set<String> sets = attributeMap.keySet();
                        sets.stream().forEach(s -> {
                            String s1 = attributeMap.get(s);
                            attributeMap.put(s, s1 + 1);
                        });
                    } else {
                        // 如果这个属性值不为空，则这个节点加一，这层的其他节点都加零
                        Set<String> sets = attributeMap.keySet();
                        sets.stream().forEach(s -> {
                            if (attributeValue.equals(s)) {
                                String s1 = attributeMap.get(s);
                                attributeMap.put(s, s1 + 1);
                            } else {
                                String s1 = attributeMap.get(s);
                                attributeMap.put(s, s1 + 0);
                            }

                        });
                    }


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });


            Set<String> set = attributeMap.keySet();
            set.stream().forEach(attributeValue -> {
                if (attributeValue != null) {
                    Node node = level.getLevelValue().get(attributeValue);
                    String s = attributeMap.get(attributeValue);
                    Long[] longArray = getLongArray(s);
                    node.setList(longArray);
                } else {
                    Node node = level.getNullLevelValue().get(attributeValue);
                    String s = attributeMap.get(attributeValue);
                    Long[] longArray = getLongArray(s);
                    node.setList(longArray);
                }

            });

            if (level.getLevelValue().size() != 0) {
                tree.add(level);
            }

        });


        return tree;


    }

    public static List<String> fieldsToString(Field[] fields) {
        ArrayList<String> list = new ArrayList<>();
        for (Field field : fields) {
            list.add(field.getName());
        }

        return list;
    }


    //获取一层所有的属性值
    public static Set<String> getLevelAttributes(String levelName, List<IdentityVo> IdentityVos) {
        Set<String> set = new HashSet<>();
        IdentityVos.stream().forEach(identityVo -> {
            try {
                Field field = IdentityVo.class.getDeclaredField(levelName);
                field.setAccessible(true);
                String o = (String) field.get(identityVo);
                set.add(o);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return set;
    }


    //将“00101001”字符串切割成Long数组
    public static Long[] getLongArray(String s) {
        int length = s.length();

        int size = length / 64 + (length % 64 > 0 ? 1 : 0);
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                list.add(s.substring(i * 64));
            } else {
                list.add(s.substring(i * 64, i * 64 + 64));
            }
        }

        Long[] longs = new Long[size];
        for (int i = 0; i < size; i++) {
            BigInteger bigInteger = new BigInteger(list.get(i), 2);

            longs[i] = bigInteger.longValue();
        }

        return longs;
    }

    public static IdentityVo getHitIdentityVo(Map<String, String> eventMap, ArrayList<Level> treeList, ArrayList<IdentityVo> sortIdentityVos) {

        for (int i = 0; i < treeList.size(); i++) {
            Level level = treeList.get(i);
            String levelName = level.getLevelName();
            String s = eventMap.get(levelName);
            Map<String, Node> levelValue = level.getLevelValue();
            Node node = levelValue.get(s);
            if (node != null) {
                if (result == null) {
//                    result=node.getList();
                    result = node.getList().clone();
                } else {
                    result = getTwoLong(result, node.getList());
                }
            } else {
                Map<String, Node> nullLevelValue = level.getNullLevelValue();
                if (nullLevelValue.size() == 0) {
                    result=null;
                    return null;
                }
                Long[] list = nullLevelValue.get(null).getList();
                result = result == null ? list.clone() : getTwoLong(result, list);
            }
        }

        int listIndex = getListIndex(result);
        if (listIndex == -1) {
            result=null;
            return null;
        }

        if (listIndex <= 64) {
            if (sortIdentityVos.size() <= 64) {
                result=null;
                return sortIdentityVos.get(sortIdentityVos.size() - listIndex);
            } else {
                result=null;
                return sortIdentityVos.get(64 - listIndex);
            }

        }

        //列表前面有多少个64
        int m = listIndex / 64;
        int n = listIndex % 64;
        if (sortIdentityVos.size() / 64 == m) {
            result=null;
            return sortIdentityVos.get(m * 64 + sortIdentityVos.size() % 64 - n);
        }

        result=null;

        return sortIdentityVos.get(m * 64 + 64 - n);

    }


    public static Long[] getTwoLong(Long[] m, Long[] n) {


        for (int i = 0; i < m.length; i++) {
            result[i] = m[i] & n[i];
        }
        return result;

    }


    public static int getListIndex(Long[] m) {
        int result = 0;
        for (int i = 0; i < m.length; i++) {
            if (m[i] != 0) {
                int digits = 0;
                while (m[i] != 0) {
                    digits++;
                    m[i] >>>= 1;
                }
                if (i == 0) {
                    return digits;
                }
                return result + digits;
            } else {
                result = result + 64;
            }
        }
        return -1;
    }


}
