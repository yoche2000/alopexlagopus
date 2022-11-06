% 55348947, Lee Yo-Che
% Input: 120, 4, 2, 280
% Output: Expected average call blocking probability is 0.1964
%         Expected average call dropping probability is 0.5470

prompt = {'Call arrival rate (call/min):','Average call holding time (min):','Average cell dwelling time (min):','Number of channels:'};
dlgtitle = 'Assignment 3';
Ans = inputdlg(prompt,dlgtitle);

Arrival_rate = str2double(Ans{1});
Hold_time = 1 / str2double(Ans{2});
Dwelling_time = 1 / str2double(Ans{3});
Channel_No = str2double(Ans{4});

MaxArrivalNo = 10000; No_Cell = 49; Prob_Handoff = 1/6;
list = zeros(5,No_Cell);

while true
    Average_Sum = Arrival_rate * No_Cell;
    delimitor = Average_Sum + sum(list(1,:)) * (Hold_time+Dwelling_time);
    
    if (rand <= Average_Sum/delimitor)        % If arrives
        sum_arv = Arrival_rate;
        for i=1:1:No_Cell
            if (rand <= sum_arv/Average_Sum)
                list(2,i) = list(2,i) + 1;
                if (list(1,i) < Channel_No)
                    list(1,i) = list(1,i) + 1;
                else
                    list(3,i) = list(3,i) + 1;
                end
                break;
            else
                sum_arv = sum_arv + Arrival_rate;
            end
        end
        call = sum(list(2,:));
        if (call >= MaxArrivalNo)
            break;
        end
    elseif (rand <= (Average_Sum+list(1,1)*Hold_time)/delimitor)
        sum_holdTime = list(1,1) * Hold_time;
        sumOfmu = Hold_time * sum(list(1,:));
        for i=1:1:No_Cell
            if (rand <= sum_holdTime/sumOfmu)
                list(1,i) = list(1,i) - 1;
                break;
            else
                sum_holdTime = sum_holdTime + list(1,i) * Hold_time;
            end
        end
    else                                    % If handover
        sum_dwellTime = list(1,1) * Dwelling_time;
        sumOfsgm = Dwelling_time * sum(list(1,:));
        for i=1:1:No_Cell
            if (rand <= sum_dwellTime/sumOfsgm)
                list(1,i) = list(1,i) - 1;
                handIn = i;
                break;
            else
                sum_dwellTime = sum_dwellTime + list(1,i) * Dwelling_time;
            end
        end
        Sum_prob = Prob_Handoff;
        for j=1:1:(1/Prob_Handoff)
            if (rand <= Sum_prob)
                handover = mod(handIn+(1/Prob_Handoff),No_Cell);
                list(4,handover) = list(4,handover)+1;
                if (list(1,handover) == Channel_No)
                    list(5,handover) = list(5,handover)+1;
                else
                    list(1,handover) = list(1,handover)+1;
                end
                break;
            else
                Sum_prob = Sum_prob + Prob_Handoff;
            end
        end
    end
end

P_block = sum(list(3,:)) / MaxArrivalNo;
P_drop = sum(list(5,:)) / sum(list(4,:));

Text = 'Average call blocking probability is %6.4f\nAverage call dropping probability is %6.4f\n';
fprintf(Text, P_block, P_drop);