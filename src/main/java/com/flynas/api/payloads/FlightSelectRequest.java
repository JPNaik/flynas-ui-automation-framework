package com.flynas.api.payloads;

import java.util.List;

public class FlightSelectRequest {
    private MarketSellBody marketSell;
    public FlightSelectRequest(){}
    public FlightSelectRequest(MarketSellBody marketSell){
        this.marketSell = marketSell;
    }
    public MarketSellBody getMarketSell() {
        return marketSell;
    }

    public void setMarketSell(MarketSellBody marketSell) {
        this.marketSell = marketSell;
    }
    public static class MarketSellBody{
        private List<String> keys;
        private boolean lockPrice;

        public MarketSellBody(){}
        public MarketSellBody(List<String> keys,boolean lockPrice)
        {
            this.keys = keys;
            this.lockPrice = lockPrice;
        }
        public List<String> getKeys() {
            return keys;
        }

        public void setKeys(List<String> keys) {
            this.keys = keys;
        }

        public boolean isLockPrice() {
            return lockPrice;
        }

        public void setLockPrice(boolean lockPrice) {
            this.lockPrice = lockPrice;
        }

    }

}
