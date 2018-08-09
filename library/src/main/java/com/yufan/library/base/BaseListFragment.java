package com.yufan.library.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yufan.library.inject.AnnotateUtils;
import com.yufan.library.inter.Vu;
import com.yufan.library.inter.VuList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengfantao on 18/3/30.
 *
 */

public abstract class BaseListFragment<V extends VuList> extends BaseFragment {
    protected List list=new ArrayList();
    protected List getList(){
        return list;
    }
    protected V vu;
    public V getVu() {
        return vu;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        try {
            vu =(V) AnnotateUtils.getVu(this).newInstance();
            vu.init(inflater, container);
            vu.setPresenter(this);
            view = vu.getView();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return view;
    }
    protected  void setAdapterData(List subList){
        if(subList!=null&&subList.size()>0){
            if(vu.getRecyclerView().getPageManager().getCurrentIndex()==0){
                list.clear();
                list.addAll(subList);
            }else{
                list.addAll(subList);
            }
        }
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }


}
