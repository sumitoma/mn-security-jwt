package mn.security.jwt.auth;

import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class AuthProvider implements AuthenticationProvider {

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flowable.create(flowableEmitter -> {
            if(authenticationRequest.getIdentity().equals("sumit") &&
                authenticationRequest.getSecret().equals("tomar")){
                UserDetails userDetails = new UserDetails((String)authenticationRequest.getIdentity(),
                        List.of("ALL"));
                flowableEmitter.onNext(userDetails);
                flowableEmitter.onComplete();
            } else{
                flowableEmitter.onError(new AuthenticationException(new AuthenticationFailed()));
            }

        }, BackpressureStrategy.ERROR);
    }
}
