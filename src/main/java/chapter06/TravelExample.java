package chapter06;

import java.util.*;
import java.util.concurrent.*;

public class TravelExample {

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public List<TravelQuote> getRankedTravelQuotes(
            TravelInfo travelInfo, Set<TravelCompany> companies,
            Comparator<TravelQuote> ranking, long time, TimeUnit unit
    ) throws InterruptedException {
        List<QuoteTask> tasks = new ArrayList<>();
        for (TravelCompany company : companies) {
            tasks.add(new QuoteTask(company, travelInfo));
        }
        List<Future<TravelQuote>> futures = executor.invokeAll(tasks);

        List<TravelQuote> quotes = new ArrayList<>(tasks.size());
        Iterator<QuoteTask> iterator = tasks.iterator();
        for (Future<TravelQuote> future : futures) {
            QuoteTask task = iterator.next();
            try {
                quotes.add(future.get());
            } catch (ExecutionException e) {
                quotes.add(task.getFailureQuote(e.getCause()));
            } catch (CancellationException e) {
                quotes.add(task.getTimeoutQuote(e));
            }
        }
        quotes.sort(ranking);
        return quotes;
    }


    private class QuoteTask implements Callable<TravelQuote> {

        private final TravelCompany company;

        private final TravelInfo travelInfo;

        private QuoteTask(TravelCompany company, TravelInfo travelInfo) {
            this.company = company;
            this.travelInfo = travelInfo;
        }

        @Override
        public TravelQuote call() throws Exception {
            return company.solicitQuote(travelInfo);
        }

        public TravelQuote getFailureQuote(Throwable cause) {
            return new TravelQuote();
        }

        public TravelQuote getTimeoutQuote(CancellationException e) {
            return new TravelQuote();
        }
    }

    private class TravelCompany {
        public TravelQuote solicitQuote(TravelInfo travelInfo) {
            return new TravelQuote();
        }
    }

    private class TravelInfo {
    }

    private class TravelQuote {
    }
}
