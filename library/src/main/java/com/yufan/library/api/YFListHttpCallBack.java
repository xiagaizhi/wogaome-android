package com.yufan.library.api;

import com.yufan.library.base.BaseListVu;
import com.yufan.library.inter.VuList;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.view.recycler.PageInfo;

import java.util.List;

/**
 * Created by mengfantao on 18/4/20.
 */

public abstract class YFListHttpCallBack extends BaseHttpCallBack {

    private PageInfo pageInfo;
    private VuList vu;
    public YFListHttpCallBack(VuList vu) {
        this.pageInfo = vu.getRecyclerView().getPageManager();
        this.vu=vu;
    }

    @Override
    public void onSuccess(ApiBean mApiBean) {
        pageInfo.next();
        pageInfo.setPageState(PageInfo.PAGE_STATE_NONE);
        onFinish();
    }

    @Override
    public void onError(int id, Exception e) {
        vu.setStateError();
        pageInfo.setPageState(PageInfo.PAGE_STATE_ERROR);
        DialogManager.getInstance().toast(e.getMessage());
        onFinish();
    }

    @Override
    public void onFinish() {

    }
}
