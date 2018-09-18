package com.yushi.leke.fragment.searcher;

/**
 * Created by mengfantao on 18/8/31.
 */

public class SearchBottomInfo {

     boolean hasMore;
    String message;

    public SearchBottomInfo(boolean hasMore, String message) {
        this.hasMore = hasMore;
        this.message = message;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
