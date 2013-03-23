package org.ss.cvtracker.service.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.ss.cvtracker.service.mail.reader.MessageBean;

public class ArchiveReader {

	public List<MessageBean> readFile(Date date, String saveFolder)
			throws IOException {
		List<MessageBean> result = new ArrayList<MessageBean>();
		Calendar dateLetter = GregorianCalendar.getInstance();
		dateLetter.setTime(date);
		SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
		SimpleDateFormat dd = new SimpleDateFormat("dd");
		SimpleDateFormat MM = new SimpleDateFormat("MM");

		String dir = saveFolder + (yyyy.format(date)) + "/" + (MM.format(date))
				+ "/" + (dd.format(date));

		File dirMsg = new File(dir);

		if (dirMsg.exists()) {

			String[] letterList = dirMsg.list();
			for (String letter : letterList) {
				String[] dataLetter = letter.split("-");
				dateLetter.add(Calendar.HOUR, Integer.parseInt(dataLetter[0]));
				dateLetter
						.add(Calendar.MINUTE, Integer.parseInt(dataLetter[1]));

				String adress = letter.substring(6, letter.length() - 5);
				Set<String> textLetter = readText(dir + "/" + letter);
				date = dateLetter.getTime();
				result.add(new MessageBean(adress, date, textLetter));

			}

		}

		return result;
	}

	static Set<String> readText(String filename) throws IOException {
		Set<String> res = new LinkedHashSet<String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String str;
			while ((str = in.readLine()) != null) {
				res.add(str);
			}
			in.close();
			return res;
		} catch (IOException e) {
			return null;
		}

	}
}
