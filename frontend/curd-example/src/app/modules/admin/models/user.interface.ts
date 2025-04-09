export interface User {
    id: number;
    name: string;
    email: string;
    address: string;
    dpi: string;
    state: string;
    roleId: number;
    createAt: string;
}

export interface Role {
    id: number;
    name: string;
    description: string;
}

export type CreateUser = Omit<User, 'id' | 'createAt'> & {
    password: string;
};
