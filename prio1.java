import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.*;
import java.awt.color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class prio1 extends JFrame implements ActionListener {
	public int n, pid, arrival_time, burst_time, priority, start_time, completion_time, turnaround_time, waiting_time, response_time;
	static int x=0, current_time = 0, prev = 0, total_TAT = 0, total_WT = 0, total_RS = 0, num_process;
	int is_completed;
	JTextField input_text;
	JButton submit_btn;
	JTable table;
	DefaultTableModel tableModel;

	public void actionPerformed(ActionEvent e) {
	}

	public static void main(String[] args) {
		JTextField input_text;
		JButton submit_btn;
		JTable table;
		DefaultTableModel tableModel;
		int y = 50,hei = 10;
		prio1.x = 50;
		JFrame f = new JFrame();
		f.setTitle("NON-PREEMPTIVE PRIORITY SCHEDULING ALGORITHM ");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
		input_text = new JTextField(10);
		submit_btn = new JButton("<html><b>SUBMIT</b><html>");
		JLabel label = new JLabel("<html><b>Enter the number of processes:     </b></html> ");
		f.add(label);
		f.add(input_text);
		f.add(submit_btn);

		JPanel parent_panel = new JPanel(); 
		parent_panel.setLayout(new BoxLayout(parent_panel, BoxLayout.Y_AXIS));

		// Create the table and table model
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Process");
		tableModel.addColumn("AT");
		tableModel.addColumn("Priority");
		tableModel.addColumn("BT");
		tableModel.addColumn("CT");
		tableModel.addColumn("TAT");
		tableModel.addColumn("WT");
		tableModel.addColumn("RT");
		table = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(450, 400));
		parent_panel.add(scrollPane);

		JLabel avg_TAT_label = new JLabel(); 
		JLabel avg_WT_label = new JLabel(); 
		JLabel gantt_label = new JLabel("<html><b>GANTT CHART :    </b></html> ");
		JPanel avg_panel = new JPanel(); 
		JPanel gantt_label_panel = new JPanel(); 
		JPanel gantt_panel = new JPanel();
		JPanel time_panel = new JPanel();

		avg_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		gantt_label_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		gantt_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 0));
		time_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		avg_panel.add(avg_TAT_label);
		avg_panel.add(avg_WT_label);
		gantt_label_panel.add(gantt_label);
		f.add(parent_panel);
		f.pack();
		f.setSize(600, 700);
		f.setVisible(true);

		submit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == submit_btn) {
					int numProcesses = Integer.parseInt(input_text.getText());
					prio1.num_process = numProcesses;
					int completed = 0;
					int n = prio1.num_process;

					prio1 obj[] = new prio1[n];
					try {
						for (int i = 0; i < n; i++) {
							obj[i] = new prio1();
							obj[i].arrival_time = Integer.parseInt(JOptionPane.showInputDialog("Enter Arrival time for Process " + (i + 1)));
							obj[i].burst_time = Integer.parseInt(JOptionPane.showInputDialog("Enter Burst time for Process " + (i + 1)));
							obj[i].priority = Integer.parseInt(JOptionPane.showInputDialog("Enter Priority for Process " + (i + 1)));
							obj[i].is_completed = 0;
							if (obj[i].burst_time == 0 || obj[i].priority == 0 || obj[i].priority > 10) {
								System.out.println("Priority or burst_time is invalid");
								f.setVisible(false);
								break;
							}
						}
					} catch (Exception exc) {
						System.out.println("");
					}
					while (completed != n) {
						int idx = -1;
						int mx = 10;
						for (int i = 0; i < n; i++) {
							if (obj[i].arrival_time <= prio1.current_time && obj[i].is_completed == 0) {
								if (obj[i].priority < mx) {
									mx = obj[i].priority;
									idx = i;
								}
								if (obj[i].priority == mx) {
									if (obj[i].arrival_time < obj[idx].arrival_time) {
										mx = obj[i].priority;
										idx = i;
									}
								}
							}
						}

						if (idx != -1) {
							if (prio1.current_time == 0) {
								JLabel lll = new JLabel();
								lll.setText(prio1.current_time + "");
								time_panel.add(lll);
							}
							obj[idx].start_time = prio1.current_time;
							obj[idx].completion_time = obj[idx].start_time + obj[idx].burst_time;
							obj[idx].turnaround_time = obj[idx].completion_time - obj[idx].arrival_time;
							obj[idx].waiting_time = obj[idx].turnaround_time - obj[idx].burst_time;
							obj[idx].response_time = obj[idx].start_time - obj[idx].arrival_time;

							prio1.total_TAT += obj[idx].turnaround_time;
							prio1.total_WT += obj[idx].waiting_time;
							prio1.total_RS += obj[idx].response_time;

							obj[idx].is_completed = 1;
							completed++;
							prio1.current_time = obj[idx].completion_time;

							JLabel l = new JLabel();
                            l.setBounds(prio1.x, y, 70, hei);
							l.setText("    P" + (idx + 1) + "    ");
							
							l.setBackground(Color.green);
							l.setOpaque(true);
							gantt_panel.add(l);

							JLabel ll = new JLabel();
                            if(obj[idx].completion_time < 10){
                                ll.setText("          0" + obj[idx].completion_time);
                            }
                            else{
                                ll.setText("           " + obj[idx].completion_time);
                            }
							ll.setBounds(prio1.x, y, 15, hei);
							time_panel.add(ll);
						} else {
							JLabel l = new JLabel();
							l.setText("          ");
							l.setBounds(prio1.x, y, 15, hei);
							l.setBackground(Color.red);
							l.setOpaque(true);
							gantt_panel.add(l);
							if (prio1.current_time == 0) {
								JLabel lll = new JLabel();
								lll.setText(prio1.current_time + "");
								time_panel.add(lll);
							}
							prio1.current_time++;
							JLabel ll = new JLabel();
                            if(prio1.current_time < 10){
                                ll.setText("        0" + prio1.current_time);
                            }
                            else{
                                ll.setText("         " + prio1.current_time);
                            }
							time_panel.add(ll);
						}
					}
					double avgTAT = (double) prio1.total_TAT / n, avgWT = (double) prio1.total_WT / n;
					for (int i = 0; i < n; i++) {
						tableModel.addRow(new Object[]{
								(i + 1),
								obj[i].arrival_time,
								obj[i].priority,
								obj[i].burst_time,
								obj[i].completion_time,
								obj[i].turnaround_time,
								obj[i].waiting_time,
								obj[i].response_time
						});
						avg_TAT_label.setText("AVERAGE TAT : " + avgTAT + "   ");
						avg_WT_label.setText("AVERAGE WT : " + avgWT);
					}

					parent_panel.add(gantt_label_panel);
					parent_panel.add(gantt_panel);
					parent_panel.add(time_panel);
					parent_panel.add(avg_panel);
				}
			}
		});
	}
}
