package controller.lotto;

import controller.lotto.view.LottoInputView;
import controller.lotto.view.LottoResultView;
import domain.common.Money;
import domain.lotto.LottoCount;
import domain.lotto.LottoFactory;
import domain.lotto.UserLotto;
import domain.lotto.WinningLotto;
import domain.lotto.genrate.CustomLottoGenerator;
import domain.lotto.genrate.LottoGenerator;
import domain.lotto.genrate.RandomGenerator;

import java.util.List;

import static domain.lotto.properties.LottoProperties.LOTTO_PRICE;

public class LottoController {
    private static final Money lottoMoney = new Money(LOTTO_PRICE);

    /**
     * 로또를 랜덤으로 구매한다.
     *
     * @param args
     */
    public static void main(String[] args) {
        Money money = Money.from(LottoInputView.createMoneyView());

        LottoCount lottoCount = createCustomLottoCount(money);

        CustomLottoGenerator customLottoGenerator = createCustomLottoGenerator(lottoCount.customLottoCount());

        UserLotto userLotto = createUserLotto(List.of(customLottoGenerator, new RandomGenerator()), lottoCount);

        LottoResultView.createUserLottoListView(userLotto.lottoList(), lottoCount);

        WinningLotto winningLotto = createWinningLotto();

        LottoResultView.createStatisticsView(LottoStatistics.from(userLotto, winningLotto), money);

    }

    private static CustomLottoGenerator createCustomLottoGenerator(Integer customLottoCount) {
        return CustomLottoGenerator.from(LottoInputView.createCustomLottoIntegerList(customLottoCount));
    }

    private static LottoCount createCustomLottoCount(Money money) {
        Integer customLottoCount = LottoInputView.createCustomLottoCount();

        Money customLottoPrice = new Money(customLottoCount * LOTTO_PRICE);
        Integer autoLottoCount = (int) lottoMoney.divide(money.minus(customLottoPrice));
        return new LottoCount(customLottoCount, autoLottoCount);
    }

    private static WinningLotto createWinningLotto() {
        List<Integer> winningIntegerList = LottoInputView.createWinningInterList();
        int bonusNumber = LottoInputView.createBonusNumberView();
        return WinningLotto.from(winningIntegerList, bonusNumber);
    }

    private static UserLotto createUserLotto(List<LottoGenerator> lottoGenerators, LottoCount lottoCount) {
        LottoFactory lottoFactory = new LottoFactory(lottoGenerators, lottoCount);
        return lottoFactory.createUserLotto();
    }
}
