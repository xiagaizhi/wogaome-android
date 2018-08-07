package ${packageName};
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;

/**
* Created by mengfantao on 18/8/2.
*/
@VuClass(${VuName}.class)
public class ${FragmentName} extends BaseListFragment<${ContractName}.View> implements ${ContractName}.Presenter{

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
super.onViewCreated(view, savedInstanceState);

}

@Override
public void onLoadMore(int index) {

}


@Override
public void onRefresh() {

}
}
