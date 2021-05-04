package com.example.demo.service.tutorialService;

import com.example.demo.model.Tutorial;
import org.springframework.data.domain.Page;

public interface TutorialService {
    Page<Tutorial> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);
}

