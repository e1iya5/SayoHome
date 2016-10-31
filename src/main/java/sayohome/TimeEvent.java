/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sayohome;

import java.util.Date;

/**
 *
 * @author elias
 */
public class TimeEvent implements Event {

    private Date body;

    public TimeEvent(Date date) {
        this.body = date;
    }

    public TimeEvent() {
        this.body = new Date();
    }

    @Override
    public Date getBody() {
        return this.body;
    }
}
