package com.example.a29149.ecollaboration.util;

import android.util.Log;

import com.example.a29149.ecollaboration.dto.TaskDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/5 0005.
 */

public class TimeSort implements Serializable{

    public static boolean chechDateTrue(String startDate, String endDate)
    {
        String[] stringsForStart=startDate.split("-");
        String[] stringsForEnd=endDate.split("-");
        if(Integer.parseInt(stringsForStart[0])<Integer.parseInt(stringsForEnd[0]))
        {
            return true;
        }
        for(int i=0;i<stringsForStart.length;i++)
        {
            if(Integer.parseInt(stringsForStart[i])>Integer.parseInt(stringsForEnd[i]))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean TimeA_XiaoYv_TimeB(String startDate, String endDate)
    {
        String[] stringsForStart=startDate.split("-");
        String[] stringsForEnd=endDate.split("-");
        if(Integer.parseInt(stringsForStart[0])<Integer.parseInt(stringsForEnd[0]))
        {
            return true;
        }
        if(Integer.parseInt(stringsForStart[0])>Integer.parseInt(stringsForEnd[0]))
        {
            return false;
        }
        if(Integer.parseInt(stringsForStart[1])>Integer.parseInt(stringsForEnd[1]))
        {
            return false;
        }
        if(Integer.parseInt(stringsForStart[1])<Integer.parseInt(stringsForEnd[1]))
        {
            return true;
        }
        if(Integer.parseInt(stringsForStart[2])<=Integer.parseInt(stringsForEnd[2]))
        {
            return true;
        }
        return false;
    }



    public static boolean checkDateIsNotInOldPlan(String thisDate, String startDate, String endDate)
    {
        if(TimeSort.chechDateTrue(startDate,thisDate)&&TimeSort.chechDateTrue(thisDate,endDate))
        {
            return false;
        }
        return true;
    }

    public static boolean checkDateToList(List<TaskDTO> taskDTOArrayList, String thisTime)
    {
        for (TaskDTO taskDTO: taskDTOArrayList) {
            Log.d("MyTimeCheck",thisTime);
            Log.d("MyTimeCheck",taskDTO.getBeginDate());
            Log.d("MyTimeCheck",taskDTO.getTargetDate());
            if(TimeSort.TimeA_XiaoYv_TimeB(taskDTO.getBeginDate(),thisTime)
                    &&TimeSort.TimeA_XiaoYv_TimeB(thisTime,taskDTO.getTargetDate()))
            {
                return false;
            }
        }
        return true;
    }


    public static int IndexOfTime(List<TaskDTO> taskDTOArrayList, String startTime)
    {
        if(taskDTOArrayList.size()==0)
        {
            return 0;
        }
        for(int i=0;i<taskDTOArrayList.size();i++)
        {
            if(!(TimeSort.TimeA_XiaoYv_TimeB(taskDTOArrayList.get(i).getBeginDate(),startTime)))
            {
                return i;
            }
        }
        return taskDTOArrayList.size();
    }
}
