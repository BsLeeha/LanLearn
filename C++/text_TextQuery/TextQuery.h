#pragma once
#include <iostream>
#include <set>
#include <map>
#include <vector>
#include <string>
#include <sstream>
#include <fstream>
#include <iterator>
#include <algorithm>
#include <cassert>
#include <memory>
#include <utility>

using namespace std;

class QueryResult;
class TextQuery {
	friend class Query;
	friend class NotQuery;
	friend class AndQuery;
	friend class OrQuery;
public:
	using lineNo = vector<string>::size_type;

	TextQuery(ifstream &file);

	QueryResult query(const string &strQuery) const;

private:
	shared_ptr<vector<string>> ptextVec;
	map<string, shared_ptr<pair<set<lineNo>, vector<lineNo>>>> wordMap;
};

class QueryResult {
	friend ostream &Print(ostream &, const QueryResult&);
	friend class Query;
	friend class QueryBase;
	friend class NotQuery;
	friend class AndQuery;
	friend class OrQuery;
public:
	using lineNo = vector<string>::size_type;
	QueryResult(string s, shared_ptr<pair<set<lineNo>, vector<lineNo>>> pp, shared_ptr<vector<string>> pv)
		: queryWord{ s }, pwordPair{ pp }, ptextVec{ pv } {}
private:
	string queryWord;
	shared_ptr<pair<set<lineNo>, vector<lineNo>>> pwordPair;
	shared_ptr<vector<string>> ptextVec;
};

string make_plural(size_t ctr, const string &word, const string &ending);

ostream &print(ostream &os, const QueryResult &qr);