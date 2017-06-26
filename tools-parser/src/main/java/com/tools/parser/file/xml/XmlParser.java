package com.tools.parser.file.xml;

import com.alibaba.fastjson.JSONObject;
import com.tools.parser.IParser;
import com.tools.parser.exception.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/30
 * @description :xml解析类
 */
public class XmlParser implements IParser {
    private static final Logger logger = LoggerFactory.getLogger(XmlParser.class);

    /**
     * 解析xml文件
     *
     * @param inputStream 流
     * @param parameter   参数(parameter 为attr是解析属性,不传则解析内容)
     * @return 解析数据
     * @throws ParserException
     */
    @Override
    public List<Object> parser(InputStream inputStream, String... parameter) throws ParserException {
        boolean flag = parseWhich(parameter);
        List<Object> datas;
        try {
            DocumentBuilderFactory buildFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = buildFactory.newDocumentBuilder();
            Document document = build.parse(inputStream);
            NodeList nList = document.getDocumentElement().getChildNodes();
            datas = new ArrayList<>(nList.getLength());
            if (flag) {
                parserAttribute(nList, datas);
            } else {
                parserContent(nList, datas);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new ParserException("解析异常...", e);
        }
        return datas;
    }

    /**
     * 解析属性
     *
     * @param nList 元素集
     * @param datas 解析数据
     */
    private void parserAttribute(NodeList nList, List<Object> datas) {
        for (int i = 0; i < nList.getLength(); i++) {
            Node element = nList.item(i);
            // 获取属性值
            NamedNodeMap nodeMap = element.getAttributes();
            logger.info(JSONObject.toJSONString(nodeMap));
            if (nodeMap == null) {
                continue;
            }
            Map<String, Object> map = new HashMap<>();
            datas.add(map);
            for (int j = 0, len = nodeMap.getLength(); j < len; j++) {
                Node node = nodeMap.item(j);
                if (node == null) {
                    continue;
                }
                List<Object> valueMap = new ArrayList<>();
                map.put(node.getNodeValue(), valueMap);
                NodeList nodeList = element.getChildNodes();
                if (nodeList == null) {
                    continue;
                }
                this.getSubAttribute(nodeMap, valueMap, nodeList);
            }
        }
    }

    /**
     * 获取子节点属性
     *
     * @param nodeMap  子节点属性名集
     * @param valueMap 数据
     * @param nodeList 子节点集
     */
    private void getSubAttribute(NamedNodeMap nodeMap, List<Object> valueMap, NodeList nodeList) {

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node subElement = nodeList.item(i);
            NamedNodeMap subNodeMap = subElement.getAttributes();
            logger.info(JSONObject.toJSONString(nodeMap));
            if (subNodeMap == null) {
                continue;
            }
            Map<String, String> attrMap = new HashMap<>();
            valueMap.add(attrMap);
            for (int l = 0; l < subNodeMap.getLength(); l++) {
                Node n = subNodeMap.item(l);
                if (n == null) {
                    continue;
                }
                attrMap.put(n.getNodeName(), n.getNodeValue());
            }
        }
    }

    /**
     * 内容解析
     *
     * @param nodeList 元素节点集
     * @param datas    解析数据
     */
    private void parserContent(NodeList nodeList, List<Object> datas) {
        parserSubContent(nodeList, datas, null, 1);
        Iterator iterator = datas.listIterator();
        while (iterator.hasNext()) {
            if (((Map) iterator.next()).size() == 0) {
                iterator.remove();
            }
        }
    }


    /**
     * 解析子节点
     *
     * @param nodeList 子节点集
     * @param datas    xml解析后的数据
     * @param map      子节点解析后的数据
     */
    private void parserSubContent(NodeList nodeList, List<Object> datas, Map<String, String> map, int floor) {
        if (map == null) {
            map = new HashMap<>();
            datas.add(map);
        }
        if (floor == 2) {
            map = new HashMap<>();
            datas.add(map);
        }
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (!"#text".equals(node.getNodeName())) {
                String value = getValue(node);
                if (value == null || !value.contains("\n")) {
                    map.put(node.getNodeName(), getValue(node));
                }
            }
            NodeList nList = node.getChildNodes();
            if (nList == null || nList.getLength() == 0) {
                continue;
            }
            this.parserSubContent(nList, datas, map, floor + 1);
        }
    }

    /**
     * 获取节点的内容
     *
     * @param node 节点
     * @return 内容
     */
    private String getValue(Node node) {
        if (node == null) {
            return null;
        }
        if (node.getFirstChild() == null) {
            return null;
        }
        return node.getFirstChild().getNodeValue();
    }

    /**
     * 解析属性,还是内容
     *
     * @param parameter 参数
     * @return boolean
     */
    private boolean parseWhich(String[] parameter) {
        return !(parameter == null || parameter.length == 0)
                && parameter.length == 1
                && "attr".equals(parameter[0]);
    }
}
