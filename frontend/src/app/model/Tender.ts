export interface Platform {
    id: number;
    link: string;
}
export interface ExStatus{
    id: number,
    label: string,
    transitions: [
        ExStatus
    ]
}
export interface IntStatus {
    id: number;
    label: string;
    transitions: string[];
}

export interface ValidFrom2 { // todo remove ?
    time: number;
}

export interface ValidTo2 {  // todo remove ?
    time: number;
}

export interface TenderUpdate{
    id: number;
    externalStatus: ExStatus;
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
    intStatus: IntStatus;
    user: User;
    created: Created;
}
export interface Company {
    id: number;
    name: string;
}
export interface Tender {
    id: number;
    documentNumber: string;
    platform: Platform;
    link: string;
    name: string;
    company: Company;
    description: string;
    latestUpdate: TenderUpdate;
    updates: TenderUpdate[];
    latestExStatus: ExStatus;
    latestIntStatus: IntStatus;
    assignedIntStatuses: AssignedIntStatus[];
}



