export interface Platform {
  id: number;
  link: string;
}

export interface Status {
  id: number,
  label: string,
  terminatesTender: any
}


export interface ValidFrom2 { // todo remove ? agree-lili
  time: number;
}

export interface ValidTo2 {  // todo remove ? agree-lili
  time: number;
}

export interface TenderUpdate {
  id: number;
  externalStatus: Status;
  validFrom: number;
  validTo: number;
  details: string;
  attachedFiles: [string];
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
  company: Company
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

}

export interface Company {
  id: number;
  name: string;
}



