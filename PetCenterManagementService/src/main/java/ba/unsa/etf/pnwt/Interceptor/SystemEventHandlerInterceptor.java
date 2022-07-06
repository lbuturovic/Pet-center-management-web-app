/*package ba.unsa.etf.pnwt.Interceptor;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
private Map<String, String> actions = new HashMap<>();
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


public class SystemEventHandlerInterceptor implements HandlerInterceptor {


    public SystemEventHandlerInterceptor() {
        actions.put("POST", "CREATE");
        actions.put("PUT", "UPDATE");
        actions.put("PATCH", "UPDATE");
        actions.put("DELETE", "DELETE");
        actions.put("GET", "GET");

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("after completion");


        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        SystemEventsServiceGrpc.SystemEventsServiceBlockingStub stub =
                SystemEventsServiceGrpc.newBlockingStub(channel);
        Instant instant = Instant.now();
        SystemEventResponse systemEventResponse = stub.log(SystemEventRequest.newBuilder()
                .setTimestamp(instant.toString())
                .setMicroservice("pet-center-management-service")
                .setUser("Vildana")
                .setAction(actions.get(request.getMethod()))
                .setResource(getResource(request.getServletPath()))
                .setResponseType(HttpStatus.valueOf(response.getStatus()).toString())
                .build());
        System.out.println("Odgovor sa servera: " + systemEventResponse.getResponsemessage());
        System.out.println("---------------------------");
        channel.shutdown();
    }

    private String getResource(String path) {
        if (path.startsWith("/api/")) {
            return path.substring(5);
        }
        return "";
    }
}*/
