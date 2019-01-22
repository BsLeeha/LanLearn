/*
 * Questions summary:
 * ifstream cannnot be const reference
 */

#include "TextQuery.h"

/* store the text lines and build word map */
TextQuery::TextQuery(ifstream &file) : ptextVec{ new vector<string> } {
	assert(file.is_open());

	//string tmp;
	//while (getline(file, tmp)) 
	//	textLines.push_back(tmp);

	//copy(istream_iterator<string>(file), istream_iterator<string>(), 
	//	 back_inserter(textLines));

	for (string line; getline(file, line);) {
		ptextVec->push_back(line);
		lineNo no = ptextVec->size() - 1;				// good
		istringstream lineStr{ line };
		for (string word; lineStr >> word; ) {
			string::size_type nexpos = -1, pos;
			do {
				pos = nexpos + 1;
				nexpos = word.find_first_of("\",.?!", pos);
				if (nexpos == pos) continue;
				string subword = word.substr(pos, nexpos - pos);
				auto &pwordPair = wordMap[subword];
				if (!pwordPair)
					pwordPair.reset(new pair<set<lineNo>, vector<lineNo>>{});
				auto &lineNoSet = pwordPair->first;
				auto &lineCntVec = pwordPair->second;
				lineNoSet.insert(no);
				lineCntVec.resize(1+max(no, lineCntVec.size()));
				lineCntVec[no]++;
			} while (nexpos != string::npos);
		}
	}
}

// cannot be const member, map
QueryResult TextQuery::query(const string &word) const {
	auto noData = make_shared<pair<set<lineNo>, vector<lineNo>>>();
	auto loc = wordMap.find(word);
	if (loc == wordMap.end())
		return QueryResult(word, noData, ptextVec);
	else
		return QueryResult(word, loc->second, ptextVec);
}

string make_plural(size_t ctr, const string &word, const string &ending) {
	return (ctr > 1) ? word + ending : word;
}

ostream &Print(ostream &os, const QueryResult &qr) {
	int wordCnt = 0;
	for (auto i : qr.pwordPair->first)
		wordCnt += qr.pwordPair->second[i];

	cout << qr.queryWord << " occurs " << wordCnt
		<< make_plural(wordCnt, " time", "s") << " in "
		<< qr.pwordPair->first.size() << " distinct "
		<< make_plural(qr.pwordPair->first.size(), "line", "s") << '\n';

	auto &lineSet = qr.pwordPair->first;
	for (auto i : lineSet) {
		auto cnt = qr.pwordPair->second[i];
		cout << "\t(" << cnt << make_plural(cnt, " time", "s") << " on line "
			<< i + 1<< ")" << (*qr.ptextVec)[i] << '\n';
	}
	return os;
}