export interface User {
    id: number;
    alias: string;
}

export interface Attempt {
    id: number;
    user: User;
    factorA: number;
    factorB: number;
    resultAttempt: number;
    correct: boolean;
}