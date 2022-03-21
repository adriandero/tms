import {Role} from "./Roles";
//import {Inject, Injectable} from "@angular/core";


export type User = {
  img?: string,
  firstname: string,
  lastname: string,
  mail: string,
  roles: Role[]
}

