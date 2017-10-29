package com.lin.app.common.lottery;

import java.util.List;

/**
 * Created by linhui on 2017/10/25.
 */
public interface FactoryLottery {
    /**
     * 修改一个集合属性
     * @param groupEntity 集合属性
     * @return 是否成功
     */
    boolean modiGroup(IGroupEntity groupEntity);

    /**
     * 创建一个集合
     * @param groupEntity 集合属性
     * @return 是否成功
     */
    boolean createGroup(IGroupEntity groupEntity);

    /**
     * 删除一个集合
     * @param groupId 集合id
     * @return 是否成功
     */
    boolean deleteGroup(long groupId);

    /**
     * 创建一个元素
     * @param element 元素属性
     * @return 是否成功
     */
    boolean createElement(IElementEntity element);

    /**
     * 删除一个元素
     * @param elementId 元素id
     * @return 是否成功
     */
    boolean deleteElement(long elementId);

    /**
     * 修改一个元素属性
     * @param element 元素属性
     * @return 是否成功
     */
    boolean modiElement(IElementEntity element);

    /**
     * 一组元素关联一个集合
     * @param elementId 元素id
     * @param groupId
     * @return 返回成功一组元素
     */
    long[] elementRelationGroup(long[] elementId, long groupId);

    /**
     * 删除一组元素关于集合的数据
     * @param elementId 元素id
     * @param groupId 集合id
     * @return 返回成功一组元素
     */
    long[] deleteByElementByGroup(long[] elementId,long groupId);

    /**
     * 获取所有已经创建的集合
     * @return 集合组
     */
    List<IGroupEntity> getAllGroups();

    /**
     * 获取全部已经创建的元素
     * @return 元素集合
     */
    List<IElementEntity> getAllElements();

    /**
     * 获取一个集合里面的所有元素
     * @param groupId
     * @return 元素集合
     */
    List<IElementEntity> getElementsByGroup(long groupId);

    /**
     * 获取目标元素已经加入的集合
     * @param elementId 元素id
     * @return 集合
     */
    List<IGroupEntity> getElementJoinGroup(long elementId);

    /**
     * 登陆
     * @param user 用户名
     *  @param pwd 密码
     * @return 获取key
     */
    String login(String user,String pwd);

    /**
     * 获取个人信息
     * @param user 用户名
     * @param key 登陆成功的密匙
     * @return 个人信息
     */
    IPersonEntity getPersonInfo(long user,String key);

    /**
     * 获取当前的周期（开奖日期）
     * @return 周期
     */
    ICycleEntity getNewCycle();

    /**
     * 根据开始时间结束时间获取周期
     * @param top
     * @param end
     * @return 周期组
     */
    List<ICycleEntity> getCycles(long top, long end);

    /**
     * 创建一个赔率
     * @param iOddsEntity 赔率属性
     * @return 是否成功
     */
    boolean createOdds(IOddsEntity iOddsEntity);

    /**
     * 修改赔率
     * @param iOddsEntity 赔率属性
     * @return 是否成功
     */
    boolean modiOdds(IOddsEntity iOddsEntity);

    /**
     * 删除一个赔率
     * @param iOddsId 赔率id
     * @return 是否成功
     */
    boolean deleteOdds(long iOddsId);

    /**
     * 创建一个订单
     * @param iBuyGroup 订单属性
     * @return 是否成功
     */
    boolean order(IBuyGroupEntity iBuyGroup);

}
