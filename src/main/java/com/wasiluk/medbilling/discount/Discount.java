package com.wasiluk.medbilling.discount;


import com.wasiluk.medbilling.treatment.TreatmentEnum;

/**
 * Created by marcin on 13-May-17.
 */
public class Discount {

    private TreatmentEnum treatment;

    private double percent;

    public Discount(TreatmentEnum treatment, double percent) {
        this.treatment = treatment;
        this.percent = percent;
    }

    public Discount() {
    }

    public Discount(double percent) {
        this.percent = percent;
    }

    public TreatmentEnum getTreatment() {
        return treatment;
    }

    public void setTreatment(TreatmentEnum treatment) {
        this.treatment = treatment;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
