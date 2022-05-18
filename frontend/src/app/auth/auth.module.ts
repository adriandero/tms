import {CommonModule} from "@angular/common";
import {APP_INITIALIZER, NgModule, Optional, SkipSelf} from "@angular/core";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import {AuthService} from "./services/auth.service";
import {appInitializer} from "./services/app-init";
import {UnauthorizedInterceptor} from "./interceptors/unauthorized.interceptor";

@NgModule({
  declarations: [],
  imports: [CommonModule],
  providers: [
    {
      provide: APP_INITIALIZER,
     useFactory: appInitializer,
      multi: true,
      deps: [AuthService],
    },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: UnauthorizedInterceptor,
      multi: true,
    },
  ],
})
export class AuthModule {
  constructor(@Optional() @SkipSelf() core: AuthModule) {
    if (core) {
      throw new Error('Auth Module can only be imported to AppModule.');
    }
  }
}
