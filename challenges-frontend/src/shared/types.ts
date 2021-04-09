export interface User {
    id: number;
    alias: string;
}

export interface Attempt {
    id: number;
    userAlias: string;
    factorA: number;
    factorB: number;
    resultAttempt: number;
    correct: boolean;
}
