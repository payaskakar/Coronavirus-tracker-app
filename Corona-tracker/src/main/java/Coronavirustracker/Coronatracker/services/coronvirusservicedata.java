package Coronavirustracker.Coronatracker.services;
import Coronavirustracker.Coronatracker.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class coronvirusservicedata {
    private static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    private List<LocationStats>allStats=new ArrayList<>();
    @PostConstruct
    @Scheduled(cron = "*****")
    public void fetchvirusdata() throws IOException, InterruptedException {
        List<LocationStats>newStats=new ArrayList<>();
        HttpClient client= HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> httpResponse=client.send(request,HttpResponse.BodyHandlers.ofString());
    StringReader csvBodyReader=new StringReader(httpResponse.body());

    Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

    for (CSVRecord record : records) {
        LocationStats LocationStat=new LocationStats();
        LocationStat.setState(record.get("Province/State"));
        LocationStat.setCountry(record.get("Country/Region"));
        int latestCases=Integer.parseInt(record.get(record.size()-1));
        int prevDayCases=Integer.parseInt(record.get(record.size()-2));
        LocationStat.setLatestTotalCases(latestCases);
        LocationStat.setDiffFromPrevDay(latestCases-prevDayCases);
         newStats.add(LocationStat);
    }
this.allStats=newStats;


    }


    }

