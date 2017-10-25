package com.lin.app.common.lottery;

import java.util.List;

/**
 * Created by linhui on 2017/10/25.
 */
public interface FactoryLottery {

    boolean modiGroup(IGroupEntity groupEntity);

    boolean createGroup(IGroupEntity groupEntity);

    boolean deleteGroup(IGroupEntity groupEntity);

    boolean createElement(IElementEntity element);

    boolean deleteElement(IElementEntity element);

    boolean modiElement(IElementEntity element);

    boolean elementRelationGroup(IElementEntity[] element, long groupId);

    List<IGroupEntity> getAllGroups();

    List<IElementEntity> getAllElements();

    List<IElementEntity> getElementsByGroup(long groupId);

    List<IGroupEntity> getElementJoinGroup(long elementId);

    String login(IPersonEntity iPersonEntity);

    IPersonEntity getPersonInfo(long personId,String key);

    ICycleEntity getNewCycle();

    List<ICycleEntity> getCycles(long top, long end);

    boolean createOdds(IOddsEntity iOddsEntity);

    boolean modiOdds(IOddsEntity iOddsEntity);

    boolean deleteOdds(IOddsEntity iOddsEntity);

    boolean order(IBuyGroupEntity iBuyGroup);

}
