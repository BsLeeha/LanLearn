#include "Query.h"
#include "TextQuery.h"
#include <algorithm>

using namespace std;

ostream &QueryBase::print(ostream &os, const TextQuery &t) {
	auto queryRes = eval(t);
	auto lineNoSet = queryRes.pwordPair->first;
	os << rep() << " occurs " << lineNoSet.size() << make_plural(lineNoSet.size(), " time", "s") << '\n';
	for (auto i : lineNoSet)
		os << "\t line " << i + 1 << ": " << (*queryRes.ptextVec)[i] << '\n';

	return os;
}

QueryResult NotQuery::eval(const TextQuery &t) const {
	using lineNo = TextQuery::lineNo;
	auto opQueryRes = opQuery.eval(t);
	auto &opLineNoSet = opQueryRes.pwordPair->first;
	auto pp = make_shared<pair<set<lineNo>, vector<lineNo>>>();
	auto &lineNoSet = pp->first;
	for (lineNo i = 0; i < t.ptextVec->size(); ++i)
		if (!opLineNoSet.count(i)) lineNoSet.insert(i);
	return QueryResult{rep(), pp, t.ptextVec};
}

QueryResult AndQuery::eval(const TextQuery &t) const {
	using lineNo = TextQuery::lineNo;
	auto lhsQueryRes = lhs.eval(t);
	auto rhsQueryRes = rhs.eval(t);
	auto &lhsLineNoSet = lhsQueryRes.pwordPair->first;
	auto &rhsLineNoSet = rhsQueryRes.pwordPair->first;

	auto pp = make_shared<pair<set<lineNo>, vector<lineNo>>>();
	auto &lineNoSet = pp->first;
	set_intersection(lhsLineNoSet.begin(), lhsLineNoSet.end(), rhsLineNoSet.begin(), rhsLineNoSet.end(),
					 inserter(lineNoSet, lineNoSet.begin()));
	return QueryResult{rep(), pp, t.ptextVec};
}

QueryResult OrQuery::eval(const TextQuery &t) const {
	using lineNo = TextQuery::lineNo;
	auto lhsQueryRes = lhs.eval(t);
	auto rhsQueryRes = rhs.eval(t);
	auto &lhsLineNoSet = lhsQueryRes.pwordPair->first;
	auto &rhsLineNoSet = rhsQueryRes.pwordPair->first;

	auto pp = make_shared<pair<set<lineNo>, vector<lineNo>>>();
	auto &lineNoSet = pp->first;
	set_union(lhsLineNoSet.begin(), lhsLineNoSet.end(), rhsLineNoSet.begin(), rhsLineNoSet.end(),
			  inserter(lineNoSet, lineNoSet.begin()));
	return QueryResult{ rep(), pp, t.ptextVec };
}