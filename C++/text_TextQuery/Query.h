#pragma once

#include "TextQuery.h"

using namespace std;

class QueryBase {
public:
	virtual QueryResult eval(const TextQuery &) const = 0;
	virtual string rep() const = 0;
	virtual ostream &print(ostream &os, const TextQuery &t);
};

class Query {
	friend Query operator ~ (const Query & operand);
	friend Query operator | (const Query & lhs, const Query &rhs);
	friend Query operator & (const Query &, const Query &);
public:
	Query(const string &str);
	Query(shared_ptr<QueryBase> qp) : q{ qp } {}

	QueryResult eval(const TextQuery &t) const { return q->eval(t); }
	string rep() const { return q->rep(); }
	ostream &print(ostream &os, const TextQuery &t) { return q->print(os, t); }

private:
	shared_ptr<QueryBase> q;
};

class WordQuery : public QueryBase {
	friend class Query;
private:
	WordQuery(const string &s) : queryWord{s} {}
	QueryResult eval(const TextQuery &t) const {
		return t.query(queryWord);
	}
	string rep() const { return queryWord; }
	ostream &print(ostream &os, const TextQuery &t) { return Print(os, eval(t));}

	string queryWord;
};

class NotQuery : public QueryBase {
	friend Query operator ~(const Query &);
private:
	NotQuery(const Query &q) : opQuery{q} {}
	QueryResult eval(const TextQuery &s) const;
	string rep() const { return "~( " + opQuery.rep() + " )"; }
	Query opQuery;
};

class BinaryQuery : public QueryBase {
	friend class AndQuery;
	friend class OrQuery;
private:
	BinaryQuery(const Query &l, const Query &r, const string &s)
		: lhs{ l }, rhs{ r }, op{ s } {} 

	string rep() const { return "( " + lhs.rep() + " " + op + " " + rhs.rep() + " )"; }
	
	Query lhs, rhs;
	string op;
};

class AndQuery : public BinaryQuery {
	friend Query operator & (const Query &, const Query &);
private:
	AndQuery(const Query &lhs, const Query &rhs) 
		: BinaryQuery(lhs, rhs, "&") {}
	QueryResult eval(const TextQuery &) const;
};

class OrQuery : public BinaryQuery {
	friend Query operator | (const Query &, const Query &);
private:
	OrQuery(const Query &lhs, const Query &rhs)
		: BinaryQuery(lhs, rhs, "|") {}
	QueryResult eval(const TextQuery &) const;
};

inline Query::Query(const string &str) : q{ new WordQuery{str} } {}

inline Query operator ~ (const Query & operand) {
	return shared_ptr<QueryBase>{new NotQuery(operand)};
}

inline Query operator | (const Query & lhs, const Query &rhs) {
	return shared_ptr<QueryBase>{new OrQuery(lhs, rhs)};
}

inline Query operator & (const Query &lhs, const Query &rhs) {
	return shared_ptr<QueryBase>{new AndQuery(lhs, rhs)};
}