package com.tools.redis.service.impl;

import com.tools.redis.common.CommonTest;
import com.tools.redis.exception.RedisException;
import com.tools.redis.service.IRedisService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/27
 * @description :
 */
public class RedisServiceImplTest extends CommonTest {


    @Autowired
    @Qualifier("redisService")
    private IRedisService redisService;

    private List<String> list = new ArrayList<>();
    private Set<String> set = new HashSet<>();
    private HashMap<String, Object> map = new HashMap<>();

    private String stringKey = "string";
    private String listKey = "list";
    private String setKey = "set";
    private String mapKey = "map";

    private List<String> cloumns = Arrays.asList(new String[]{"userID","account","password","passwordSalt","accountStatus","lastLoginTime","lastLoginIdentity","lastLoginPlatform","lastLoginPromoChannel","lastLoginPromoCode","lastLoginIP","lastLoginVersion","lastLoginMethod","lastLoginGPSCoord","jhLastLoginTime","jhLastLoginPlatform","jhLastLoginIP","jhLastLoginVersion","jhLastLoginMethod","hrLastLoginTime","hrLastLoginPlatform","hrLastLoginIP","hrLastLoginVersion","hrLastLoginMethod","regTime","regPlatform","regPromoChannel","regPromoCode","regIP","regVersion","regGPSCoord","hrCompleteInfoTime","hrCompleteInfoPlatform","hrCompleteInfoIP","hrCompleteInfoVersion","hrCompleteInfoGPSCoord","jhCompleteInfoTime","jhCompleteInfoPlatform","jhCompleteInfoIP","jhCompleteInfoVersion","jhCompleteInfoGPSCoord","name","avatar","jhIdentityStatus","gender","birthdate","marriage","education","joinWorkDate","email","mobile","province","city","district","qq","wechat","resumeUpdateTime","website","hrIdentityStatus","companyID","position","isHRAuth","hrEmail","telNr","allowContactDay","allowContactStartTime","allowContactEndTime","allowHideResume","allowPushJob","allowSearch","allowSMSNotify","allowWechatNotify","manualTag","algoTag","algoCategoryCode","algoCategoryName","resumeSignatureCode","resumeQualityPro","resumeQualityAglo","commercialization","resumeType","dayjccnt","createTime","updateTime","isSimilarAuth","birthYear","hrGreetID","jhGreetID","isFocusHrByCRM","focusHrByCRMTime","hrCRMOwner","jhCRMTag","communicateStatus","recentAppoTime","isNewAppo","focusJhByCRMTime","jhCRMOwner","isFocusJhByCRM","recentContactTime","planContactTime","resumeCategory","jhLastLoginPromoChannel","jhLastLoginPromoCode","hrLastLoginPromoChannel","hrLastLoginPromoCode","hrCompletePromoChannel","hrCompletePromoCode","jhCompletePromoChannel","jhCompletePromoCode","jhRegTime","jhRegPlatform","jhRegPromoChannel","jhRegPromoCode","jhRegVersion","jhRegIP","jhRegMethod","jhRegGPSCoord","hrRegTime","hrRegPlatform","hrRegPromoChannel","hrRegPromoCode","hrRegVersion","hrRegIP","hrRegMethod","hrRegGPSCoord","inviterID","crmCustomerType","isPublic","isAdvisor","jhCurrentGPS","distributionChannel","isRPOMarketing","RPOInterviewDate","RPOMarketingTime","advisorCallFlag","advisorCallTime","advisorCallFlat"});

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < 5; i++) {
            list.add(i + "list");
            set.add(i + "set");
            map.put(i + "", "map");
        }


    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void testGet() throws Exception {
        List object =redisService.getObjectList("test1");
        System.out.println(String.valueOf(object).length());
    }
    @Test
    public void testSetObjectString() throws Exception {
        redisService.setObject(stringKey, "string");
    }

    @Test
    public void testSetObjectSerializer() throws Exception {

    }

    @Test
    public void testSetObjectList() throws Exception {
        redisService.setObject("test", cloumns);
        redisService.expire("test",1000);
    }

    @Test
    public void testSetObjectSet() throws Exception {
        redisService.setObject(setKey, set);
    }

    @Test
    public void testSetObjectMap() throws Exception {
        redisService.setObject(mapKey, map);
    }


    @Test
    public void testAddFirst() throws Exception {
        redisService.addFirst(listKey, new String[3]);
    }

    @Test
    public void testAddLast() throws Exception {
       Long value= redisService.addLast(listKey, list.toArray(new String[list.size()]));
        System.out.println(value);

    }

    @Test
    public void testPollFirst() throws Exception {
       String value= redisService.pollFirst(listKey);
        System.out.println(value);
    }

    @Test
    public void testPollLast() throws Exception {
        String value =redisService.pollLast(listKey);
        System.out.println(value);
    }

    @Test
    public void testGetList() throws RedisException {
        List<String> list = redisService.getList(listKey);
        System.out.println(list);
    }

    @Test
    public void testSort() throws RedisException {
        List<String> list = redisService.sort(listKey);
        System.out.println(list);
    }
    @Test
    public void testDel() throws Exception {
        redisService.del(listKey);
    }

    @Test
    public void testExpire() throws Exception {
        redisService.expire(stringKey, 20);
    }

    @Test
    public void testTtl() throws Exception {
        redisService.ttl(stringKey);
    }

}