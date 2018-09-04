package com.yushi.leke.fragment.ucenter.personalInfo;

import android.widget.EditText;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;
import com.yufan.library.base.VuList;

import java.util.List;

/**
 * Created by zhanyangyang on 18/8/25.
 */

public interface PersonalInfoContract {
    interface IView extends VuList {
        void showCityPickerView();

        void showGenderPickerView(List<String> genderList);

        void setCurrentInputBox(EditText editText);

    }

    interface Presenter extends Pr {
        void openPlayer();

        void resetTab();

        void upTab(PersonalItem personalItem);

        void resetVu();

        void selectedCityInfo(int options1, int options2, int options3);

        void selectGender(String gender);

        void tohideSoftInput();

        void toSubmitData(String content);

        void choosePhotos();
    }
}
