package net.sf.esfinge.greenframework.mock.service.sameclasstest;

import net.sf.esfinge.greenframework.configuration.GreenFactory;

public class RecommendationService {

    private RecommendationDAO userDao = GreenFactory.greenify(RecommendationDAO.class);

    public void findRecommendation(StringBuilder sb) {
        sb.append(userDao.findProduct())
                .append(" received ")
                .append(userDao.findVisits())
                .append(" visits this month.\n Also check out ")
                .append(userDao.findOtherProduct())
                .append("!");
    }

}
