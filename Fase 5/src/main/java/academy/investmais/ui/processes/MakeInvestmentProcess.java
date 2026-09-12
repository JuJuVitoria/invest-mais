package academy.investmais.ui.processes;

import academy.investmais.repository.AssetRepository;

public class MakeInvestmentProcess {
    private AssetRepository assetsDatabase;
    public void menuMakeInvestment() {
        System.out.println("Investimentos disponíveis:");
        //System.out.println(assetsDatabase.getAvalibleAssets());
    }
}
