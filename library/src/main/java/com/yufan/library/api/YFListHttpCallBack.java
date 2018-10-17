package com.yufan.library.api;

import com.yufan.library.base.VuList;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.view.recycler.PageInfo;

/**
 * Created by mengfantao on 18/4/20.
 */

public abstract class YFListHttpCallBack extends BaseHttpCallBack {

    private PageInfo pageInfo;
    private VuList vu;
    private boolean havaRequestSuccess;

    public YFListHttpCallBack(VuList vu) {
        this.pageInfo = vu.getRecyclerView().getPageManager();
        this.vu = vu;
    }

    @Override
    public void onSuccess(ApiBean mApiBean) {
        pageInfo.next();
        pageInfo.setPageState(PageInfo.PAGE_STATE_NONE);
        havaRequestSuccess = true;
    }

    @Override
    public void onError(int id, Exception e) {
        vu.setStateError();
        pageInfo.setPageState(PageInfo.PAGE_STATE_ERROR);
        DialogManager.getInstance().toast(e.getMessage());

    }

    @Override
    public void onFinish() {
        if (!havaRequestSuccess) {
            vu.setStateError();
            pageInfo.setPageState(PageInfo.PAGE_STATE_ERROR);
        }
        if( vu.getRecyclerView().getPTR()!=null){
            vu.getRecyclerView().getPTR().refreshComplete();
        }

    }
}
