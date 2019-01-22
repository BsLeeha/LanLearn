#include <iostream>
#include "TextQuery.h"
#include "Query.h"

using namespace std;

int main()
{
	string tmp;
	ifstream fileStream{ "file.txt" };
	TextQuery tq{ fileStream };

	//while (true) {
	//	cout << "input the word to look for, or q to quit: ";
	//	string s;
	//	if (!(cin >> s) || s == "q") break;
	//	print(cout, tq.query(s)) << endl;
	//}

	Query q{"Daddy"};
	Query q1{"Alice"};
	Query q2{"hair"};
	Query q3{"fiery"};
	Query q4{"bird"};
	Query q5{"wind"};
	//q.print(cout, tq);
	//(~q1).print(cout, tq);
	//(q1 & q2).print(cout, tq);
	(q3 & q4 | q5).print(cout, tq);
}