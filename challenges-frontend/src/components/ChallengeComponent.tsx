import {Attempt} from '../shared/types';

import React, { useState, useEffect } from 'react';
import ChallengesApiClient from '../services/ChallengesApiClient';
import {LastAttemptsComponent} from './LastAttemptsComponent';
import {LeaderBoardComponent} from './LeaderBoardComponent';

const emptyAttemptArray: Attempt[] = [];

const initValues = {
    factorA: 0,
    factorB: 0,
    userAlias: '',
    message: '',
    guess: 0,
    lastAttempts: emptyAttemptArray,
    attemptCounts: 0,
};
export function ChallengeComponent() {
    const [values, setValues] = useState(initValues);

    useEffect(() => {
                       ChallengesApiClient.challenge().then((res) => {
                           if (res.ok) {
                               res.json().then((json) => {
                                   setValues({
                                       ...values,
                                       factorA: json.factorA,
                                       factorB: json.factorB,
                                   });
                               });
                           } else setValues({ ...values, message: 'Error: server error or not available'});
                       });
                   }, [values.attemptCounts]);

//     const refreshChallenge = () => {
//          ChallengesApiClient.challenge().then((res) => {
//              if (res.ok) {
//                  res.json().then((json) => {
//                      setValues({
//                          ...values,
//                          factorA: json.factorA,
//                          factorB: json.factorB,
//                      });
//                  });
//              } else setValues({ ...values, message: 'Error: server error or not available'});
//          });
//      }

    const handleSubmitResult = (e: React.SyntheticEvent) => {
        e.preventDefault();
        ChallengesApiClient.sendGuess(values.userAlias, values.factorA, values.factorB, values.guess).then((res) => {
            if (res.ok) {
                res.json().then((json) => {
                    if (json.correct) {
                        let counts = values.attemptCounts;
                        counts++;
                        setValues({ ...values, message: 'Congratulations! Your guess is correct', attemptCounts: counts });
                    }
                    else
                        setValues({
                            ...values,
                            message: `Oops! Your guess ${json.resultAttempt} is wrong, but keep playing!`,
                        });
                        updateLastAttempts(values.userAlias);
                });
            } else setValues({ ...values, message: 'Error: server error or not available' });
        });
    };

    const updateLastAttempts = (userAlias: string) => {
        ChallengesApiClient.getAttempts(userAlias).then(res => {
            if(res.ok){
                const attempts: Attempt[] = [];
                res.json().then(data => {
                    data.forEach(item => {
                        attempts.push(item);
                    });
                    setValues({...values, lastAttempts: attempts});
                });
            }
        });
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => setValues({ ...values, [e.target.name]: e.target.value });

    return (
        <div className="display-column">
            <div>
                <h3>Your new challenge is</h3>
                <h1>
                    {values.factorA} x {values.factorB}
                </h1>
            </div>
            <form onSubmit={handleSubmitResult}>
                <label>
                    Your alias:
                    <input type="text" maxLength={12} name="userAlias" value={values.userAlias} onChange={handleChange} />
                </label>
                <br />
                <label>
                    Your guess:
                    <input type="number" min="0" name="guess" value={values.guess} onChange={handleChange} />
                </label>
                <br />
                <input type="submit" value="Submit" />
            </form>
            <br />
            <h4>{values.message}</h4>
            <br />
            {values.lastAttempts.length > 0 &&
                <LastAttemptsComponent lastAttempts={values.lastAttempts}/>
            }
            <br />
            <LeaderBoardComponent />
        </div>
    );
}
