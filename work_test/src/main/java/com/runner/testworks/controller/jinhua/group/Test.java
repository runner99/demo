package com.runner.testworks.controller.jinhua.group;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weizhenqiang
 * @date 2024/3/22 9:24
 */
@Slf4j
public class Test {

    public static void main(String[] args) {


        CurrentAuthVO currentAuthVO = JSONObject.parseObject(" {\n" +
                "    \"authId\": \"32\",\n" +
                "    \"authGroupName\": \"义乌市数管中心\"\n" +
                "  }", CurrentAuthVO.class);
        log.info("request gateway response currentAuthVO :{}", currentAuthVO.toString());


        List<AuthGroupItemVO> dataSource = JSONArray.parseArray("[\n" +
                "    {\n" +
                "      \"authId\": \"28\",\n" +
                "      \"authGroupName\": \"金华市保障对象\",\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"authId\": \"29\",\n" +
                "          \"authGroupName\": \"兰溪市大数据发展中心\",\n" +
                "          \"children\": []\n" +
                "        },\n" +
                "        {\n" +
                "          \"authId\": \"30\",\n" +
                "          \"authGroupName\": \"永康市大数据发展中心\",\n" +
                "          \"children\": []\n" +
                "        },\n" +
                "        {\n" +
                "          \"authId\": \"31\",\n" +
                "          \"authGroupName\": \"东阳市大数据发展中心\",\n" +
                "          \"children\": []\n" +
                "        },\n" +
                "        {\n" +
                "          \"authId\": \"32\",\n" +
                "          \"authGroupName\": \"义乌市数管中心\",\n" +
                "          \"children\": []\n" +
                "        },\n" +
                "        {\n" +
                "          \"authId\": \"33\",\n" +
                "          \"authGroupName\": \"武义县大数据发展中心\",\n" +
                "          \"children\": []\n" +
                "        },\n" +
                "        {\n" +
                "          \"authId\": \"34\",\n" +
                "          \"authGroupName\": \"婺城区大数据发展中心\",\n" +
                "          \"children\": []\n" +
                "        },\n" +
                "        {\n" +
                "          \"authId\": \"35\",\n" +
                "          \"authGroupName\": \"浦江县大数据发展中心\",\n" +
                "          \"children\": []\n" +
                "        },\n" +
                "        {\n" +
                "          \"authId\": \"36\",\n" +
                "          \"authGroupName\": \"金东区大数据发展中心\",\n" +
                "          \"children\": []\n" +
                "        },\n" +
                "        {\n" +
                "          \"authId\": \"37\",\n" +
                "          \"authGroupName\": \"磐安县大数据发展中心\",\n" +
                "          \"children\": []\n" +
                "        },\n" +
                "        {\n" +
                "          \"authId\": \"38\",\n" +
                "          \"authGroupName\": \"开发区大数据发展中心\",\n" +
                "          \"children\": []\n" +
                "        },\n" +
                "        {\n" +
                "          \"authId\": \"39\",\n" +
                "          \"authGroupName\": \"市大数据局\",\n" +
                "          \"children\": [\n" +
                "            {\n" +
                "              \"authId\": \"40\",\n" +
                "              \"authGroupName\": \"城市大脑\",\n" +
                "              \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "              \"authId\": \"41\",\n" +
                "              \"authGroupName\": \"金华市消防救援支队\",\n" +
                "              \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "              \"authId\": \"42\",\n" +
                "              \"authGroupName\": \"市科技局\",\n" +
                "              \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "              \"authId\": \"43\",\n" +
                "              \"authGroupName\": \"市委统战部\",\n" +
                "              \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "              \"authId\": \"44\",\n" +
                "              \"authGroupName\": \"市公安局\",\n" +
                "              \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "              \"authId\": \"45\",\n" +
                "              \"authGroupName\": \"市人力社保局\",\n" +
                "              \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "              \"authId\": \"46\",\n" +
                "              \"authGroupName\": \"市建设局\",\n" +
                "              \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "              \"authId\": \"47\",\n" +
                "              \"authGroupName\": \"市交通局\",\n" +
                "              \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "              \"authId\": \"48\",\n" +
                "              \"authGroupName\": \"市市场监管局（知识产权局）\",\n" +
                "              \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "              \"authId\": \"49\",\n" +
                "              \"authGroupName\": \"市委政法委\",\n" +
                "              \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "              \"authId\": \"50\",\n" +
                "              \"authGroupName\": \"市卫健委\",\n" +
                "              \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "              \"authId\": \"51\",\n" +
                "              \"authGroupName\": \"市经信委\",\n" +
                "              \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "              \"authId\": \"52\",\n" +
                "              \"authGroupName\": \"市发改委\",\n" +
                "              \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "              \"authId\": \"53\",\n" +
                "              \"authGroupName\": \"市委宣传部\",\n" +
                "              \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "              \"authId\": \"54\",\n" +
                "              \"authGroupName\": \"市行政执法局\",\n" +
                "              \"children\": []\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]", AuthGroupItemVO.class);

        List<AuthGroupItemVO> curentTree = getCurentTree(currentAuthVO.getAuthId(), dataSource);

        System.out.println("aaaa");
    }

    private static List<AuthGroupItemVO> getCurentTree(String authId, List<AuthGroupItemVO> list){
        ArrayList<AuthGroupItemVO> authGroupItemVOS = new ArrayList<>();

        for (AuthGroupItemVO vo:list){
            if (vo.getAuthId().equals(authId)){
                authGroupItemVOS.add(vo);
                break;
            }
            if (CollectionUtil.isNotEmpty(vo.getChildren())){
                authGroupItemVOS.addAll(getCurentTree(authId,vo.getChildren()));
            }
        }

        return authGroupItemVOS;

    }
}
