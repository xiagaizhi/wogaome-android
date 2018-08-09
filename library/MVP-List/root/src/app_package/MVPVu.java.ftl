package ${packageName};
import ${PackageId}.R;
import android.view.View;
import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.view.recycler.YFRecyclerView;

/**
* Created by mengfantao on 18/8/2.
*/
@FindLayout(layout = R.layout.layout_fragment_list)
@Title("${TitleName}")
public class ${VuName} extends BaseListVu<${ContractName}.Presenter> implements ${ContractName}.IView{
@FindView(R.id.recyclerview)
private YFRecyclerView mYFRecyclerView;
@Override
public void initView(View view) {

}
@Override
public void initStatusLayout(StateLayout stateLayout) {
super.initStatusLayout(stateLayout);
}

@Override
public boolean initTitle(AppToolbar appToolbar) {
return super.initTitle(appToolbar);
}
@Override
public YFRecyclerView getRecyclerView() {
return mYFRecyclerView;
}
}
