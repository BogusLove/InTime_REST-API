package com.bogusnazar.intime.Services;

import com.bogusnazar.intime.DAO.MeetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Artem on 5/18/2018.
 */
@Service
public class MeetServiceImpl implements MeetService {

    @Autowired
    private MeetRepository meetRepository;


}
