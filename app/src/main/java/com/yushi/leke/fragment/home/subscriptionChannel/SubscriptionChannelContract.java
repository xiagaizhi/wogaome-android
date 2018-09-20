package com.yushi.leke.fragment.home.subscriptionChannel;

import com.yufan.library.base.Pr;
import com.yufan.library.base.VuList;


/**
 * Created by zhanyangyang on 18/8/2.
 */

public interface SubscriptionChannelContract {
    interface IView extends VuList {

    }

    interface Presenter extends Pr {
        String getChannelName();
    }
}
