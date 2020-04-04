#include<bits/stdc++.h>
#include <fstream>
#include <sstream>
using namespace std;
int main(int argc,char *argv[]){
    string realOp = argv[1];
    string userOp = argv[2];
    ifstream realF(realOp);
    ifstream userF(userOp);
    realOp.clear();
    userOp.clear();
    bool SW = false;
    if(realF){
        ostringstream ss;
        ss << realF.rdbuf();
        realOp = ss.str();
    }
    else{
        SW = true;
    }
    if(userF){
        ostringstream ss;
        ss << userF.rdbuf();
        userOp = ss.str();
    }
    else{
        SW = true;
    }
    if(SW){
        cout << "SW";
    }
    else{
        int realIdx = 0,userIdx = 0;
        bool verdict = true;
        while(realIdx < realOp.size() && userIdx < userOp.size()){
            while(realIdx < realOp.size()){
                if(realOp[realIdx] == '\n' || realOp[realIdx] == ' ') realIdx++;
                else break;
            }
            while(userIdx < userOp.size()){
                if(userOp[userIdx] == '\n' || userOp[userIdx] == ' ') userIdx++;
                else break;
            }
            if(realIdx < realOp.size() && userIdx < userOp.size()){
                if(realOp[realIdx] != userOp[userIdx]) verdict = false;
                realIdx++;
                userIdx++;
            }
        }
        if(realIdx < realOp.size()){
            while(realIdx < realOp.size()){
                if(realOp[realIdx] == ' ' || realOp[realIdx] == '\n') realIdx++;
                else break;
            }
            if(realIdx < realOp.size()) verdict = false;
        }
        if(userIdx < userOp.size()){
            while(userIdx < userOp.size()){
                if(userOp[userIdx] == ' ' || userOp[userIdx] == '\n') userIdx++;
                else break;
            }
            if(userIdx < userOp.size()) verdict = false;
        }
        if(verdict) cout << "AC";
        else cout << "WA";
    }
}