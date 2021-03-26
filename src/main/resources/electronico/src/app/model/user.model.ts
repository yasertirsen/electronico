export interface User {
  userId: number;
  fullName: string;
  username: string;
  email: string;
  password: string;
  address: string;
  enabled: boolean;
  role: string;
  authorities: string[];
  isLocked: boolean;
  expiresIn: number;
  token: string;
}
