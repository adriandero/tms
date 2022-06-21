import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../auth/services/auth.service";
import {finalize, Subscription} from "rxjs";
import {TenderService} from "../../services/tender.service";
import {BackendResponse} from "../../model/protocol/Response";
import {Tender} from "../../model/Tender";
import {stringify} from "querystring";
import { User } from 'src/app/auth';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})


export class LoginComponent implements OnInit, OnDestroy {
  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  pwdFormControl = new FormControl('', [Validators.required]);
  busy = false;
  mail = '';
  password = '';
  loginError = false;
  private subscription: Subscription | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private tenderService: TenderService
  ) {
  }

  ngOnInit(): void {
    this.subscription = this.authService.user$.subscribe((x) => {
      if (this.route.snapshot.url[0].path === 'login') {
        const accessToken = localStorage.getItem('access_token');
        const refreshToken = localStorage.getItem('refresh_token');
        if (x && accessToken && refreshToken) {
          const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '';
          this.router.navigate([returnUrl]);
        }
      } // optional touch-up: if a tab shows login page, then refresh the page to reduce duplicate login
    });
  }

  login() {
    console.log(this.mail)
    if (!this.mail || !this.password) {
      return;
    }
    this.busy = true;
    const returnUrl = this.route.snapshot.queryParams['returnUrl'] || 'home';
    this.authService
      .login(this.mail, this.password)
      .pipe(finalize(() => (this.busy = false)))
      .subscribe(
        () => {
          console.log("here")

          this.tenderService.getUser(this.mail).subscribe({
            next: (sent: any) => {
              const response: BackendResponse<User> = sent;
              let user: User = response.body
              sessionStorage.setItem("user", JSON.stringify(user) )
              this.router.navigate([returnUrl]);

            },
          });

        },
        () => {
          console.log("error")
          this.loginError = true;
        }
      );
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}

