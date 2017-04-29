package com.example.a29149.ecollaboration.model.message.newmessage.sort;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/14 0014.
 */

public class SelectedList implements Serializable {
    private List<SortModel> sortModels;

    public List<SortModel> getSortModels() {
        return sortModels;
    }

    public void setSortModels(List<SortModel> sortModels) {
        this.sortModels = sortModels;
    }
}
