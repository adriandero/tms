export interface Platform {
  id: number;
  link: string;
}

export interface Status {
  id: number;
  label: string;
  terminatesTender: any;
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

export interface User {
  id: number;
  mail: string;
  role: Role;
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
}

export interface Company {
  id: number;
  name: string;
}

export interface Filter{ //TODO kann es mehr als einen ext oder int status geben?
   platforms : string[];
   companies : string[];
   titles :   string[];
   intStatus :   string[];
   extStatus :   string[];
   files :   string[];
   uptDetails :   string[];
   users :   string[];
}
