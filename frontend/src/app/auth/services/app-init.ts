import {Observable, Subscription} from 'rxjs';
import {AuthService} from "./auth.service";

export function appInitializer(
  authService: AuthService
): () => boolean {
  return () => true; //authService.refreshToken();
}
