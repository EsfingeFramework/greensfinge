package net.sf.esfinge.greenframework.core.integrationtest.sameclass.mock;

import net.sf.esfinge.greenframework.core.configuration.GreenFactory;

public class RecommendationService {

    private final RecommendationDAO userDao = GreenFactory.greenify(RecommendationDAO.class);

    public void findRecommendation(StringBuilder sb) {
        sb.append(userDao.findProduct())
                .append(" received ")
                .append(userDao.findVisits())
                .append(" visits this month.\n Also check out ")
                .append(userDao.findOtherProduct())
                .append("!");
    }

}
