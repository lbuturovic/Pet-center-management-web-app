package ba.unsa.etf.pnwt.petcenter.Models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Visit {
    private String startDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
