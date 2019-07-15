package com.ivlie7.submission.view;

import com.ivlie7.submission.base.BaseView;

public interface SearchDataView<T> extends BaseView {
    void getData(T data);
}
