package com.flynas.api.payloads;

import java.util.List;

public class FlightSearchRequest {

    private FlightSearchBody flightSearch;

    public FlightSearchRequest(){}
    public FlightSearchRequest(FlightSearchBody flightSearch){
        this.flightSearch = flightSearch;
    }
    public FlightSearchBody getFlightSearch() {
        return flightSearch;
    }

    public void setFlightSearch(FlightSearchBody flightSearch) {
        this.flightSearch = flightSearch;
    }


    public static class FlightSearchBody {
        private String specialDiscount;
        private int adultCount;
        private int childCount;
        private String flightMode;
        private List<FlightDetails> flights;
        private int infantCount;
        private String selectedCurrencyCode;

        public FlightSearchBody() {
        }

        public FlightSearchBody(String specialDiscount, int adultCount, int childCount, String flightMode, List<FlightDetails> flights,
                                int infantCount, String selectedCurrencyCode) {
            this.specialDiscount = specialDiscount;
            this.adultCount = adultCount;
            this.childCount = childCount;
            this.flightMode = flightMode;
            this.flights = flights;
            this.infantCount = infantCount;
            this.selectedCurrencyCode = selectedCurrencyCode;
        }

        public List<FlightDetails> getFlights() {
            return flights;
        }

        public void setFlights(List<FlightDetails> flights) {
            this.flights = flights;
        }


        public String getSpecialDiscount() {
            return specialDiscount;
        }

        public void setSpecialDiscount(String specialDiscount) {
            this.specialDiscount = specialDiscount;
        }

        public int getAdultCount() {
            return adultCount;
        }

        public void setAdultCount(int adultCount) {
            this.adultCount = adultCount;
        }

        public int getChildCount() {
            return childCount;
        }

        public void setChildCount(int childCount) {
            this.childCount = childCount;
        }

        public String getFlightMode() {
            return flightMode;
        }

        public void setFlightMode(String flightMode) {
            this.flightMode = flightMode;
        }

        public int getInfantCount() {
            return infantCount;
        }

        public void setInfantCount(int infantCount) {
            this.infantCount = infantCount;
        }

        public String getSelectedCurrencyCode() {
            return selectedCurrencyCode;
        }

        public void setSelectedCurrencyCode(String selectedCurrencyCode) {
            this.selectedCurrencyCode = selectedCurrencyCode;
        }
    }


}
