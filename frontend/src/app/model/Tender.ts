import { User } from "./User";

export interface Platform {
  id: number;
  link: string;
}

export interface Status {
  id?: number; // id is only present on external status
  label: string;
  terminatesTender: any;
  transitions: Status[];
}

export interface ValidFrom2 {
  // todo remove ? agree-lili
  time: number;
}

export interface ValidTo2 {
  // todo remove ? agree-lili
  time: number;
}

export interface TenderUpdate {
  id: number;
  externalStatus: Status;
  validFrom: any;
  validTo: any;
  details: string;
  attachedFiles: Attachement[];
}
export interface Attachement {
  id: number;
  fileName: string;
  fileSize: number;
}

export interface Role {
  id: number;
  designation: string;
}


export interface Group{
  id : number,
  name : string
}
export interface Created {
  time: number;
}

export interface AssignedIntStatus {
  id: number;
  internalStatus: Status;
  user: User;
  created: Created;
}

export interface Tender {
  assignedIntStatuses: AssignedIntStatus[];
  company: Company;
  description: string;
  documentNumber: string;
  id: number;
  latestExtStatus: Status;
  latestIntStatus: Status;
  latestUpdate: TenderUpdate;
  link: string;
  name: string;
  platform: Platform;
  updates: TenderUpdate[];
  predictionAccuracy: number;
  predictedIntStatus: string;

  intStatusTransitions: Status[] | undefined,
}

export interface Company {
  id: number;
  name: string;
}

export interface Filter{
   platforms : string[];
   companies : string[];
   titles :   string[];
   intStatus :   string[];
   extStatus :   string[];
   files :   string[];
   uptDetails :   string[];
   users :   string[];
   startDate : Date | null;
   endDate : Date | null;
   sortBy : string | "DEFAULT";
}

export interface Assignment {
  id :number;
  user : User;
  tender : Tender;
  instruction: string;
  hasUnseenChanges? : boolean | true;
}
