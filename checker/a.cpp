#include<bits/stdc++.h>
#define ll long long
#define mod 1000000007
using namespace std;
ll xyp(ll x,ll y){
	if(y == 0) return 1LL;
	if(y == 1) return x;
	if(y % 2){
		ll p = xyp(x,y - 1);
		return (x * p) % mod;
	}
	ll p = xyp(x,y / 2);
	return (p * p) % mod;
}
void f(){
while(1){return;}
f();}
int main(){
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	ll t;
	cin >> t;
	while(t--){
		ll n;
		cin >> n;
		ll a[n];
		ll maxx = 0;
		for(ll i = 0;i < n;i++){
			cin >> a[i];
			maxx = max(a[i],maxx);
		}
		maxx++;
		ll base = 1;
		ll num = 0;
		for(ll i = n - 1;i >= 0;i--){
			num = (num + (base * a[i]) % mod) % mod;
			base = (base * maxx) % mod;
		}
		cout << num << '\n';
	}
	f();
}
